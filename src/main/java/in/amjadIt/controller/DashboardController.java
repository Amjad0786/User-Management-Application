package in.amjadIt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import in.amjadIt.dto.Quote;

@Controller
public class DashboardController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @GetMapping("/quote")
    public String getQuote(Model model) {
        try {
            String url = env.getProperty("my.app.quote.url"); // e.g., https://api.quotable.io/random
            ResponseEntity<Quote> response = restTemplate.getForEntity(url, Quote.class);
            Quote quote = response.getBody();
            
            if (quote != null) {
                System.out.println("Quote Fetched: " + quote);
                model.addAttribute("quote", quote);
            } else {
                model.addAttribute("error", "No quote data available.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Unable to fetch the quote at the moment.");
            e.printStackTrace();  // Log the exception for debugging purposes
        }
        return "Dashboard";
    }
}
