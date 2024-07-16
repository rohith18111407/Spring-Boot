package com.ProductManagement.beststore.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ProductManagement.beststore.models.Product;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin/jpa/products")
public class ExcelDownloadController {

    private final ExcelService excelService;

    @Autowired
    public ExcelDownloadController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/excelDownload")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response) {
        String excelFileName = "products.xlsx"; // Filename for the downloaded file

        try {
            // Temporarily store the Excel file in a directory (e.g., downloads folder)
            String downloadsFolder = System.getProperty("user.home") + "/Downloads/";
            String excelFilePath = downloadsFolder + excelFileName;

            // Call service method to generate Excel file
            List<Product> products = getProductList(); // Replace with actual product fetching logic
            excelService.exportProductsToExcel(products, excelFilePath);

            // Set response content type
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // Set header for the file attachment
            response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);

            // Copy the file content to the response output stream
            FileInputStream fileInputStream = new FileInputStream(excelFilePath);
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            fileInputStream.close();

            // Delete the temporary file after download
            Path fileToDeletePath = Paths.get(excelFilePath);
            Files.deleteIfExists(fileToDeletePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 // Endpoint to import products from uploaded Excel file
    @PostMapping("/excelUpload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.getOriginalFilename().endsWith(".xlsx")) {
            try {
                excelService.importProductsFromExcel(file);
                redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("message", "Failed to upload file: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Please upload an Excel file (.xlsx)");
        }
        return "redirect:/admin/jpa/products"; // Redirect to home page or appropriate view
    }
    
    // Method to fetch products (replace with actual logic)
    private List<Product> getProductList() {
        return excelService.getAllProducts(); // Replace with your service method to fetch products
    }
}

