package kz.kasya.realq.controllers.rest;

import kz.kasya.realq.models.entities.Images;
import kz.kasya.realq.models.entities.Jobs;
import kz.kasya.realq.repositories.JobRepository;
import kz.kasya.realq.services.ImageService;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Optional;

/**
 * @author Assylkhan
 * on 11.05.2019
 * @project logistic_server
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${image.not.found.src}")
    String NO_IMAGE;

    private JobRepository jobRepository;
    private ImageService imageService;

    @Autowired
    public ImageController(JobRepository jobRepository,
                           ImageService imageService) {
        this.imageService = imageService;
        this.jobRepository = jobRepository;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity fileUpload(@RequestParam("file") MultipartFile file, @RequestParam Long jobId)
            throws IOException {
        Optional<Jobs> jobOpt = jobRepository.findById(jobId);
        if (!jobOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Jobs job = jobOpt.get();
        Images image = imageService.getByJobId(jobId);
        if (image == null) {
            image = new Images();
            String folders = "var/tmp/";
            String path = folders + new Date().getTime() + file.getOriginalFilename();
            File convertFile = new File(path);
            new File(folders).mkdirs();
            convertFile.createNewFile();
            FileUtils.writeByteArrayToFile(convertFile, file.getBytes());
            System.out.println(path);
            image.setPath(path);
            image.setJob(job);
            imageService.add(image);
        } else {
            String folders = "var/tmp/";
            String path = folders + new Date().getTime() + file.getOriginalFilename();
            File convertFile = new File(path);
            new File(folders).mkdirs();
            if (convertFile.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File not created");
            }
            FileUtils.writeByteArrayToFile(convertFile, file.getBytes());
            String oldImage = new String(image.getPath());
            image.setPath(path);
            image.setJob(job);
            imageService.update(image,oldImage);
        }

        return ResponseEntity.status(HttpStatus.OK).body(image);
    }

    @GetMapping(value = "{id}")
    public @ResponseBody
    ResponseEntity<byte[]> getFile(@PathVariable Long id) throws IOException {
        Images image = imageService.getByJobId(id);
        File file = null;
        if (image != null) {
            String filename = image.getPath();
            file = new File(filename);
        } else {
            file = new ClassPathResource(NO_IMAGE).getFile();
        }

        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        MediaType mediaType = null;
        if (mimeType.trim().equalsIgnoreCase(MediaType.IMAGE_JPEG_VALUE)) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (mimeType.trim().equalsIgnoreCase(MediaType.IMAGE_PNG_VALUE)) {
            mediaType = MediaType.IMAGE_PNG;
        }
        return ResponseEntity.ok().contentType(mediaType).body(Files.readAllBytes(file.toPath()));
    }
}