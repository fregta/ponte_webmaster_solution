package hu.ponte.hr;

import hu.ponte.hr.domain.Image;
import hu.ponte.hr.repositories.ImageRepository;
import hu.ponte.hr.services.ImageStore;
import hu.ponte.hr.services.SignService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author zoltan
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class SignTest
{
	Map<String, String> files = new LinkedHashMap<>() {
		{
			put("cat.jpg", "XYZ+wXKNd3Hpnjxy4vIbBQVD7q7i0t0r9tzpmf1KmyZAEUvpfV8AKQlL7us66rvd6eBzFlSaq5HGVZX2DYTxX1C5fJlh3T3QkVn2zKOfPHDWWItdXkrccCHVR5HFrpGuLGk7j7XKORIIM+DwZKqymHYzehRvDpqCGgZ2L1Q6C6wjuV4drdOTHps63XW6RHNsU18wHydqetJT6ovh0a8Zul9yvAyZeE4HW7cPOkFCgll5EZYZz2iH5Sw1NBNhDNwN2KOxrM4BXNUkz9TMeekjqdOyyWvCqVmr5EgssJe7FAwcYEzznZV96LDkiYQdnBTO8jjN25wlnINvPrgx9dN/Xg==");
			put("enhanced-buzz.jpg", "tsLUqkUtzqgeDMuXJMt1iRCgbiVw13FlsBm2LdX2PftvnlWorqxuVcmT0QRKenFMh90kelxXnTuTVOStU8eHRLS3P1qOLH6VYpzCGEJFQ3S2683gCmxq3qc0zr5kZV2VcgKWm+wKeMENyprr8HNZhLPygtmzXeN9u6BpwUO9sKj7ImBvvv/qZ/Tht3hPbm5SrDK4XG7G0LVK9B8zpweXT/lT8pqqpYx4/h7DyE+L5bNHbtkvcu2DojgJ/pNg9OG+vTt/DfK7LFgCjody4SvZhSbLqp98IAaxS9BT6n0Ozjk4rR1l75QP5lbJbpQ9ThAebXQo+Be4QEYV/YXf07WXTQ==");
			put("rnd.jpg", "lM6498PalvcrnZkw4RI+dWceIoDXuczi/3nckACYa8k+KGjYlwQCi1bqA8h7wgtlP3HFY37cA81ST9I0X7ik86jyAqhhc7twnMUzwE/+y8RC9Xsz/caktmdA/8h+MlPNTjejomiqGDjTGvLxN9gu4qnYniZ5t270ZbLD2XZbuTvUAgna8Cz4MvdGTmE3MNIA5iavI1p+1cAN+O10hKwxoVcdZ2M3f7/m9LYlqEJgMnaKyI/X3m9mW0En/ac9fqfGWrxAhbhQDUB0GVEl7WBF/5ODvpYKujHmBAA0ProIlqA3FjLTLJ0LGHXyDgrgDfIG/EDHVUQSdLWsM107Cg6hQg==");
		}
	};

	@Mock
	private ImageRepository imageRepository;

	private ImageStore imageStore;

	@Test
	public void test_01_cat() throws IOException, SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		imageStore = new ImageStore(imageRepository,new SignService());
		File imageFile = new File("src/test/resources/images/cat.jpg");
		FileInputStream input = new FileInputStream(imageFile);
		MultipartFile multipartFile = new MockMultipartFile("cat.jpg",imageFile.getName(),"image/jpg", IOUtils.toByteArray(input));

		when(imageRepository.save(any(Image.class))).thenAnswer(returnsFirstArg());

		Image testResult = imageStore.storeImage(multipartFile);

		assertEquals(testResult.getEncodedDigitalSignature(),files.get(testResult.getFileName()));

	}

	@Test
	public void test_02_buzz() throws IOException, SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		imageStore = new ImageStore(imageRepository,new SignService());
		File imageFile = new File("src/test/resources/images/enhanced-buzz.jpg");
		FileInputStream input = new FileInputStream(imageFile);
		MultipartFile multipartFile = new MockMultipartFile("enhanced-buzz.jpg",imageFile.getName(),"image/jpg", IOUtils.toByteArray(input));

		when(imageRepository.save(any(Image.class))).thenAnswer(returnsFirstArg());

		Image testResult = imageStore.storeImage(multipartFile);

		assertEquals(testResult.getEncodedDigitalSignature(),files.get(testResult.getFileName()));

	}

	@Test
	public void test_03_rnd() throws IOException, SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		imageStore = new ImageStore(imageRepository,new SignService());
		File imageFile = new File("src/test/resources/images/rnd.jpg");
		FileInputStream input = new FileInputStream(imageFile);
		MultipartFile multipartFile = new MockMultipartFile("rnd.jpg",imageFile.getName(),"image/jpg", IOUtils.toByteArray(input));

		when(imageRepository.save(any(Image.class))).thenAnswer(returnsFirstArg());

		Image testResult = imageStore.storeImage(multipartFile);

		assertEquals(testResult.getEncodedDigitalSignature(),files.get(testResult.getFileName()));
	}


}
