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

    @GetMapping("/binarysearch")
    public FormatResponse binarySearch(@RequestParam String list, @RequestParam String value) {
        String[] elements = list.split(",");
        int index = binarySearchRecursive(elements, value, 0, elements.length - 1);
        return new FormatResponse("binarySearch", list, value, String.valueOf(index));
    }

    private int binarySearchRecursive(String[] elements, String value, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;

        if (elements[mid].equals(value)) {
            return mid;
        } else if (elements[mid].compareTo(value) < 0) {
            return binarySearchRecursive(elements, value, mid + 1, high);
        } else {
            return binarySearchRecursive(elements, value, low, mid - 1);
        }
    }
    

}
