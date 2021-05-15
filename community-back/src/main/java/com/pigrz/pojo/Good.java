package com.pigrz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Good {
    private int id;
    private BigDecimal price;
    private String imagepath;
    private String name;
    private String type;
}
