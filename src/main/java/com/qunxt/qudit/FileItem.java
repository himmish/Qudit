package com.qunxt.qudit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
public class FileItem implements Serializable {
    String name;
    boolean isDirectory;
}
