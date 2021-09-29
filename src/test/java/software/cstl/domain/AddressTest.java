package software.cstl.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import software.cstl.utils.CodeNodeErpUtils;
import software.cstl.web.rest.TestUtil;

import java.io.IOException;
import java.nio.charset.Charset;

public class AddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = new Address();
        address1.setId(1L);
        Address address2 = new Address();
        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);
        address2.setId(2L);
        assertThat(address1).isNotEqualTo(address2);
        address1.setId(null);
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    public void fontTest() throws IOException {
        String main= "gÄzi-B-gi‡k`";
        String convert = "মঞ্জুর-ই-মোর্শেদ";
        System.out.println(CodeNodeErpUtils.convertToANSI(convert));
        System.out.println(new String("মঞ্জুর-ই-মোর্শেদ".getBytes(Charset.forName("utf-8"))));
    }
}
