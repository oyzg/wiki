package com.oyzg.wiki.req;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EbookQueryReq extends PageReq{
    private Long id;

    private String name;

}