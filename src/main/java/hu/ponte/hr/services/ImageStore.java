package hu.ponte.hr.services;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.domain.Image;
import hu.ponte.hr.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImageStore {

    private final ImageRepository imageRepository;
    private final SignService signService;

    @Autowired
    public ImageStore(ImageRepository imageRepository, SignService signService) {
        this.imageRepository = imageRepository;
        this.signService = signService;
    }

    public Image storeImage(MultipartFile image) throws SignatureException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {

        Image imageToSave = new Image(image);

        imageToSave = signService.addAndEncodeSignature(image,imageToSave);

        return imageRepository.save(imageToSave);
    }

    public List<ImageMeta> getImageMetaList() {
        return imageRepository.findAll().stream().map(ImageMeta::new).collect(Collectors.toList());
    }

    public Image getImage(String id) {
        return imageRepository.findById(Long.valueOf(id)).orElseThrow(EntityNotFoundException::new);
    }


}
