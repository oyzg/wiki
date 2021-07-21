package com.oyzg.wiki.req;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PageReq {
    private int page;

    private int size;

}