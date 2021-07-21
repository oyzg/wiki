package com.oyzg.wiki.req;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EbookReq extends PageReq{
    private Long id;

    private String name;

}