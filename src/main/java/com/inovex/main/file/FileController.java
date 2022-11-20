package com.inovex.main.file;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.inovex.main.json.response.ResponseData;

@Controller
public class FileController {
    @Value("${base.url}")
    private String baseurl;
    @Autowired
    FileStorageProperties fileStorageProperties;
    @Autowired
    private FileService fileService;
    @Autowired
    FileStorageService fileStorageService;

    @RequestMapping(value = "/admin/upload-employee-nid", method = RequestMethod.POST)
    @ResponseBody
    public String submitNIDphoto(HttpServletRequest request, Model model,
            @RequestParam("input-iconic1") MultipartFile file, Principal principal)
            throws ParseException, InvalidFileException, IOException {

        String filename = null;
        DecimalFormat df2 = new DecimalFormat("#.##");
        List<File> filelist = new ArrayList<>();

        try {
            Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            String dir = fileStorageLocation.toString();
            if (Files.exists(Paths.get(dir + "/" + file.getOriginalFilename()))) {
                Files.deleteIfExists(Paths.get(dir + "/" + file.getOriginalFilename()));
            }
            filename = file.getOriginalFilename();
            String ext = fileService.getFileExtension(filename);
            double bytes = file.getSize();
            double kilobytes = (bytes / 1024);
            double mb = (kilobytes / 1024);

            df2.setRoundingMode(RoundingMode.UP);
            String size = df2.format(bytes);
            File fl = new File();
            fl.setFileName(filename);
            fl.setFileExtension(ext);
            fl.setFileSize(size);
            fl.setUserId("");
            fl.setFileDirectory(fileStorageLocation.toString());
            fl.setType(ext);
            fl.setCreatedAt(new Date());
            fl.setUpdatedAt(new Date());
            fileService.uploadFile(file, fileStorageLocation.toString());
            fileService.save(fl);
            filelist.add(fl);

        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }

        JSONObject obj = new JSONObject();
        obj.put("name", filename);
        return obj.toString();
    }

    @RequestMapping(value = "/admin/uploadtest", method = RequestMethod.POST)
    @ResponseBody
    public String editOrg1(HttpServletRequest request, Model model, @RequestParam("input-iconic") MultipartFile file,
            Principal principal) throws ParseException, InvalidFileException, IOException {

        String filename = null;
        DecimalFormat df2 = new DecimalFormat("#.##");
        List<File> filelist = new ArrayList<>();

        try {
            Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            String dir = fileStorageLocation.toString();
            if (Files.exists(Paths.get(dir + "/" + file.getOriginalFilename()))) {
                Files.deleteIfExists(Paths.get(dir + "/" + file.getOriginalFilename()));

                /*
                 * JSONObject obj = new JSONObject(); obj.put("name", "error"); return
                 * obj.toString();
                 */

            }
            filename = file.getOriginalFilename();
            String ext = fileService.getFileExtension(filename);
            double bytes = file.getSize();
            double kilobytes = (bytes / 1024);
            double mb = (kilobytes / 1024);

            df2.setRoundingMode(RoundingMode.UP);
            String size = df2.format(bytes);
            File fl = new File();
            fl.setFileName(filename);
            fl.setFileExtension(ext);
            fl.setFileSize(size);
            fl.setCreatedAt(new Date());
            fl.setUserId("");
            fl.setFileDirectory(fileStorageLocation.toString());
            fl.setType(ext);
            fl.setCreatedAt(new Date());
            fl.setUpdatedAt(new Date());

            fileService.uploadFile(file, fileStorageLocation.toString());
            fileService.save(fl);
            filelist.add(fl);

        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }

        JSONObject obj = new JSONObject();
        obj.put("name", filename);
        return obj.toString();
    }

    @RequestMapping(value = "/admin/upload-license", method = RequestMethod.POST)
    @ResponseBody
    public String uploadLicense(HttpServletRequest request, Model model,
            @RequestParam("input-iconic1") MultipartFile file, Principal principal)
            throws ParseException, InvalidFileException, IOException {

        String username = principal.getName();

        String filename = null;
        DecimalFormat df2 = new DecimalFormat("#.##");
        List<File> filelist = new ArrayList<>();

        try {
            Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            String dir = fileStorageLocation.toString();
            if (Files.exists(Paths.get(dir + "/" + file.getOriginalFilename()))) {
                Files.deleteIfExists(Paths.get(dir + "/" + file.getOriginalFilename()));
            }
            filename = file.getOriginalFilename();
            String ext = fileService.getFileExtension(filename);
            double bytes = file.getSize();
            double kilobytes = (bytes / 1024);
            double mb = (kilobytes / 1024);

            df2.setRoundingMode(RoundingMode.UP);
            String size = df2.format(bytes);
            File fl = new File();
            fl.setFileName(filename);
            fl.setFileExtension(ext);
            fl.setFileSize(size);
            fl.setUserId(username);
            fl.setFileDirectory(fileStorageLocation.toString());
            fl.setType(ext);
            fl.setCreatedAt(new Date());
            fl.setUpdatedAt(new Date());
            fileService.uploadFile(file, fileStorageLocation.toString());
            fileService.save(fl);
            filelist.add(fl);

        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }

        JSONObject obj = new JSONObject();
        obj.put("name", filename);
        return obj.toString();
    }

    @RequestMapping(value = "/admin/upload-nid", method = RequestMethod.POST)
    @ResponseBody
    public String uploadNID(HttpServletRequest request, Model model, @RequestParam("input-iconic2") MultipartFile file,
            Principal principal) throws ParseException, InvalidFileException, IOException {

        String username = principal.getName();

        String filename = null;
        DecimalFormat df2 = new DecimalFormat("#.##");
        List<File> filelist = new ArrayList<>();

        try {
            Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            String dir = fileStorageLocation.toString();
            if (Files.exists(Paths.get(dir + "/" + file.getOriginalFilename()))) {
                Files.deleteIfExists(Paths.get(dir + "/" + file.getOriginalFilename()));
            }
            filename = file.getOriginalFilename();
            String ext = fileService.getFileExtension(filename);
            double bytes = file.getSize();
            double kilobytes = (bytes / 1024);
            double mb = (kilobytes / 1024);

            df2.setRoundingMode(RoundingMode.UP);
            String size = df2.format(bytes);
            File fl = new File();
            fl.setFileName(filename);
            fl.setFileExtension(ext);
            fl.setFileSize(size);
            fl.setCreatedAt(new Date());
            fl.setUserId(username);
            fl.setFileDirectory(fileStorageLocation.toString());
            fl.setType(ext);
            fl.setCreatedAt(new Date());
            fl.setUpdatedAt(new Date());
            fileService.uploadFile(file, fileStorageLocation.toString());
            fileService.save(fl);
            filelist.add(fl);

        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }

        JSONObject obj = new JSONObject();
        obj.put("name", filename);
        return obj.toString();
    }

    @RequestMapping(value = "/admin/upload-pdf", method = RequestMethod.POST)
    @ResponseBody
    public String savePdf(HttpServletRequest request, Model model, @RequestParam("input-iconic1") MultipartFile file,
            Principal principal) throws ParseException, InvalidFileException, IOException {

        String username = principal.getName();
        String filename = null;
        DecimalFormat df2 = new DecimalFormat("#.##");
        List<File> filelist = new ArrayList<>();

        try {
            Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            String dir = fileStorageLocation.toString();
            if (Files.exists(Paths.get(dir + "/" + file.getOriginalFilename()))) {
                Files.deleteIfExists(Paths.get(dir + "/" + file.getOriginalFilename()));
            }
            filename = file.getOriginalFilename();
            System.out.println();
            String ext = fileService.getFileExtension(filename);
            double bytes = file.getSize();
            double kilobytes = (bytes / 1024);
            double mb = (kilobytes / 1024);

            df2.setRoundingMode(RoundingMode.UP);
            String size = df2.format(bytes);
            File fl = new File();
            fl.setFileName(filename);
            fl.setFileExtension(ext);
            fl.setFileSize(size);
            fl.setUserId(username);
            fl.setFileDirectory(fileStorageLocation.toString());
            fl.setType(ext);
            fl.setCreatedAt(new Date());
            fl.setUpdatedAt(new Date());
            fileService.uploadFile(file, fileStorageLocation.toString());
            fileService.save(fl);
            filelist.add(fl);

        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }

        /*
         * long orgId = (long) request.getSession().getAttribute("orgId");
         * Optional<Organization> org = organizationService.findById(orgId); List<File>
         * orgfilelist = new ArrayList<>(); if (org.isPresent()) { orgfilelist =
         * org.get().getFile(); orgfilelist.addAll(filelist);
         * organizationService.save(org.get()); }
         * 
         * } else {
         * 
         * }
         */

        JSONObject obj = new JSONObject();
        obj.put("name", filename);
        return obj.toString();
    }

    @RequestMapping(value = "/admin/save-image", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData saveImage(HttpServletRequest request, Model model, @RequestBody List<FileModel> files,
            Principal principal) throws ParseException, InvalidFileException, IOException {
        ResponseData responseData = new ResponseData();
        String filename = null;
        DecimalFormat df2 = new DecimalFormat("#.##");
        List<File> filelist = new ArrayList<>();

        try {
            for (FileModel file : files) {
                Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
                String dir = fileStorageLocation.toString();
                if (Files.exists(Paths.get(dir + "/" + file.getName().getOriginalFilename()))) {
                    Files.deleteIfExists(Paths.get(dir + "/" + file.getName().getOriginalFilename()));
                }
                filename = file.getName().getOriginalFilename();
                String ext = fileService.getFileExtension(filename);
                double bytes = file.getName().getSize();

                double kilobytes = (bytes / 1024);
                double mb = (kilobytes / 1024);

                df2.setRoundingMode(RoundingMode.UP);
                String size = df2.format(bytes);
                File fl = new File();
                fl.setFileName(filename);
                fl.setFileExtension(ext);
                fl.setFileSize(size);
                fl.setFileDirectory(fileStorageLocation.toString());
                fl.setType(ext);
                fl.setCreatedAt(new Date());
                fl.setUpdatedAt(new Date());
                fileService.uploadFile(file.getName(), fileStorageLocation.toString());
                fileService.save(fl);
                filelist.add(fl);

            }
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }
        responseData.setMessage("Ok");
        responseData.setData(filelist);
        responseData.setStatusCode(200);
        return responseData;
    }

    @RequestMapping(value = "/admin/delete-file", method = RequestMethod.POST)
    @ResponseBody
    public String deletefile(HttpServletRequest request, Model model, @RequestParam("key") long key,
            Principal principal) throws ParseException, InvalidFileException, IOException {

        try {
            Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            String dir = fileStorageLocation.toString();
            String fileName = null;
            Optional<File> file = fileService.findFileNameById(key);
            if (file.isPresent()) {
                fileName = file.get().getFileName();
            }
            fileService.deleteRef(key);
            fileService.delete(key);
            System.out.println("fileName " + fileName);
            Files.deleteIfExists(Paths.get(dir + "/" + fileName));
        } catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch (IOException e) {
            System.out.println("Invalid permissions.");
        }

        JSONObject obj = new JSONObject();
        return obj.toString();
    }

    @RequestMapping("/admin/file-mgt-home")
    ModelAndView filetest(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin-pages/file-upload/test");

        model.addAttribute("baseurl", baseurl);
        return modelAndView;
    }

    @GetMapping("/api/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            String path = resource.getFile().getAbsolutePath();
            System.out.println("path is : " + path);
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
            System.out.println("contentType null");
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header("attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @GetMapping("/api/downloadFile1/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile1(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            String path = resource.getFile().getAbsolutePath();
            System.out.println("path is : " + path);
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
            System.out.println("contentType null");
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
