package fr.armen.outils.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.armen.outils.models.TestForm;

@Controller
public class MainController { 

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final String UPLOAD_DIR = "./uploads/";


    @GetMapping(value = {"/"})
    public String index(Model  model) {
        model.addAttribute("titrePage", "Accueil"); 
        List<String> fileFormats = Arrays.asList("pain.001.001.03.sct", "pain.001.001.02.sct", "cfonb.160.ict", "mt101.ict"); 
        model.addAttribute("fileFormats", fileFormats);

        model.addAttribute("message", "test");

        return "index";
    }


    @GetMapping(value = {"/test"})
    public String test(Model  model) {
        model.addAttribute("titrePage", "test"); 

        model.addAttribute("message", "test");
        return "test";
    }

    @GetMapping(value = {"/form"})
    public String form(Model  model, TestForm testForm) {
        model.addAttribute("titrePage", "test");
        List<String> fileFormats = Arrays.asList("pain.001.001.03.sct", "pain.001.001.02.sct", "cfonb.160.ict", "mt101.ict"); 
        model.addAttribute("fileFormats", fileFormats);
        model.addAttribute("testForm", testForm);

        return "form";
    }

    @PostMapping("/form") 
    public String formPost(TestForm testForm, RedirectAttributes attributes) {
        logger.info("Hello");
        logger.info("Parameters are " + testForm);
        MultipartFile file = testForm.getFichier();
         // check if file is empty
         if (file == null || file.isEmpty()) {
            attributes.addFlashAttribute("message", "Pas de fichier");
            return "redirect:/form";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename() + UUID.randomUUID());
        // save the file on the local file system
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');


        try {
            logger.info("Suppression du fichier ...");
            Files.delete(Paths.get(UPLOAD_DIR + fileName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "redirect:/form";

        // return "form";
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handle(Exception ex) {
        return new ResponseEntity<String>("Exception "+ ex.getMessage(), HttpStatus.CREATED);
    }
}
