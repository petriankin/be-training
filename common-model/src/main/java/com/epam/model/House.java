package com.epam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class House {

    private String id;

    @NotNull
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 symbols long")
    private String name;
}
