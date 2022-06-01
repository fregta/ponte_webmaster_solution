package hu.ponte.hr.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Image {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String mimeType;

    private Long size;

    @Column(columnDefinition = "TEXT")
    private String encodedDigitalSignature;


    @Lob
    private byte[] data;

    public Image() {
    }

    public Image(MultipartFile image) {
        this.fileName = image.getOriginalFilename();
        this.mimeType = image.getContentType();
        this.size = image.getSize();
    }
}
