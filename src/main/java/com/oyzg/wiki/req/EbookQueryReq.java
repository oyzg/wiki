package com.oyzg.wiki.req;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EbookQueryReq extends PageReq{
    private Long id;

    private String name;

    private Long categoryId2;

}