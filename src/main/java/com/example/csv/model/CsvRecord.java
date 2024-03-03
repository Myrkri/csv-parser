package com.example.csv.model;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import com.github.mygreen.supercsv.annotation.CsvPartial;
import com.github.mygreen.supercsv.annotation.constraint.CsvNumberMax;
import lombok.Data;

@CsvBean(header = true)
@CsvPartial(columnSize = 4)
@Data
public class CsvRecord {

    @CsvColumn(label = "ID")
    @CsvNumberMax(value = "10")
    private int id;

    @CsvColumn(label = "Name")
    private String firstName;

    @CsvColumn(label = "Last Name")
    private String lastName;

}
