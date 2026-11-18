package com.example.studentmanagementapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // -------------------------------------------------------
    // In-memory store – pre-loaded with 5 sample products
    // -------------------------------------------------------
    private final List<Product> products = new ArrayList<>(List.of(
            new Product(1L, "Laptop",    "Electronics", 55999.00, 10),
            new Product(2L, "Wireless Mouse", "Electronics", 799.00, 50),
            new Product(3L, "Mechanical Keyboard", "Electronics", 2499.00, 30),
            new Product(4L, "27-inch Monitor", "Electronics", 18999.00, 15),
            new Product(5L, "Noise-Cancelling Headphones", "Electronics", 4999.00, 25)
    ));

    // Counter used to assign unique IDs to newly added products
    private long nextId = 6L;

    // -------------------------------------------------------
    // GET /api/products  – return all products
    // -------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(products);
    }

    // -------------------------------------------------------
    // GET /api/products/{id}  – return one product by ID
    // Returns 404 if not found
    // -------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> found = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with id: " + id);
        }
        return ResponseEntity.ok(found.get());
    }

    // -------------------------------------------------------
    // POST /api/products  – add a new product
    // Returns 400 if name or category is missing,
    //         or if price / quantity are invalid
    // -------------------------------------------------------
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        // Basic input validation
        if (product.getName() == null || product.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Product name is required.");
        }
        if (product.getCategory() == null || product.getCategory().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Product category is required.");
        }
        if (product.getPrice() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Price cannot be negative.");
        }
        if (product.getQuantity() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Quantity cannot be negative.");
        }

        product.setId(nextId++);
        products.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // -------------------------------------------------------
    // PUT /api/products/{id}  – update an existing product
    // Returns 404 if not found
    // Returns 400 if input is invalid
    // -------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                            @RequestBody Product updated) {
        // Input validation
        if (updated.getName() == null || updated.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Product name is required.");
        }
        if (updated.getCategory() == null || updated.getCategory().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Product category is required.");
        }
        if (updated.getPrice() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Price cannot be negative.");
        }
        if (updated.getQuantity() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Quantity cannot be negative.");
        }

        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setName(updated.getName());
                product.setCategory(updated.getCategory());
                product.setPrice(updated.getPrice());
                product.setQuantity(updated.getQuantity());
                return ResponseEntity.ok(product);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product not found with id: " + id);
    }
}
