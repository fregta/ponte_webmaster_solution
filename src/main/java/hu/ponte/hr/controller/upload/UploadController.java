package hu.ponte.hr.controller.upload;

import hu.ponte.hr.exceptions.FileStorageException;
import hu.ponte.hr.services.ImageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequestMapping("api/file")
public class UploadController {

    private final ImageStore imageStore;

    @Autowired
    public UploadController(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    @ResponseBody
    public String handleFormUpload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.getSize() > 2000000){
            throw new FileStorageException("Image size is over 2MB. Please choose a smaller image");
        }
        imageStore.storeImage(file);
        return "uploaded";
    }
}
