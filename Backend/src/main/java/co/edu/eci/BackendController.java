package co.edu.eci;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendController {

    @GetMapping("/linearsearch")
    public FormatResponse linearSearch(@RequestParam String list, @RequestParam String value) {
        String[] elements = list.split(",");
        int index = -1;

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals(value)) {
                index = i;
                break;
            }
        }

        return new FormatResponse("linearSearch", list, value, String.valueOf(index));
    }
    

}
