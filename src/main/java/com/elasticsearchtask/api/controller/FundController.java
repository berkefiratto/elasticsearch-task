package com.elasticsearchtask.api.controller;

import com.elasticsearchtask.business.service.FundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/funds")
public class FundController {

    // I didn't use RestController bc of Thymeleaf UI.

    private FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    @GetMapping
    public String getAdminPage() {
        return "fund-admin";
    }

    @PostMapping("/import")
    public String importFromExcel(@RequestParam("file") MultipartFile file, Model model) {

        fundService.importFromExcel(file);
        model.addAttribute("message", "Excel başarıyla import edildi.");
        return "fund-admin";

    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String umbrella,
            @RequestParam(required = false) Double minReturn1Y,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fundCode") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            Model model
    ) {
        var result = fundService.search(q, umbrella, minReturn1Y, page, size, sort, direction);

        model.addAttribute("results", result.getContent());
        model.addAttribute("query", q);
        model.addAttribute("umbrella", umbrella);
        model.addAttribute("minReturn1Y", minReturn1Y);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);

        return "fund-admin";
    }
}
