package com.antgroup.antchain.xbuilders.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitionSessionBO implements Serializable {
    private static final long serialVersionUID = 8347933144731894464L;

    /**
     * token的状态
     */
    private int tokenStatus;

    /**
     * 扫码的uid
     */
    private String scannedUid;
}
