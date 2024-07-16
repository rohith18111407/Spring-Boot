package com.ProductManagement.beststore.controller;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ProductManagement.beststore.models.Product;
import com.ProductManagement.beststore.repository.ProductRepository;

@Service
public class ExcelService {
    
    @Autowired
    private ProductRepository productRepository;

    // Export Products to Excel
    public void exportProductsToExcel(List<Product> products, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        int rowIndex = 0;
        for (Product product : products) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getBrand());
            row.createCell(3).setCellValue(product.getCategory());
            row.createCell(4).setCellValue(product.getPrice());
            row.createCell(5).setCellValue(product.getDescription());
            row.createCell(6).setCellValue(product.getImageFileName());
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            workbook.write(fileOutputStream);
        } finally {
            workbook.close();
        }
    }

    // Import Products from Excel
    public void importProductsFromExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet

        Map<String, Product> existingProductsMap = new HashMap<>();
        List<Product> existingProducts = productRepository.findAll();
        for (Product product : existingProducts) {
            existingProductsMap.put(product.getName(), product);
        }

        for (Row row : sheet) {
            // Assuming columns are in order: id, name, brand, category, price, description, imageFileName
            int productId = (int) row.getCell(0).getNumericCellValue();
            String productName = row.getCell(1).getStringCellValue();

            // Check if product with same name already exists
            Product existingProduct = existingProductsMap.get(productName);

            if (existingProduct != null) {
                // Product already exists, update its fields
                existingProduct.setBrand(row.getCell(2).getStringCellValue());
                existingProduct.setCategory(row.getCell(3).getStringCellValue());
                existingProduct.setPrice(row.getCell(4).getNumericCellValue());
                existingProduct.setDescription(row.getCell(5).getStringCellValue());
                existingProduct.setImageFileName(row.getCell(6).getStringCellValue());

                // Save the updated product
                productRepository.save(existingProduct);
            } else {
                // Product doesn't exist, create a new one
                Product product = new Product();
                product.setId(productId); // Assuming productId is unique
                product.setName(productName);
                product.setBrand(row.getCell(2).getStringCellValue());
                product.setCategory(row.getCell(3).getStringCellValue());
                product.setPrice(row.getCell(4).getNumericCellValue());
                product.setDescription(row.getCell(5).getStringCellValue());
                product.setImageFileName(row.getCell(6).getStringCellValue());

                // Save the product using your repository
                productRepository.save(product);
            }
        }

        workbook.close();
    }

    // Method to fetch all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}