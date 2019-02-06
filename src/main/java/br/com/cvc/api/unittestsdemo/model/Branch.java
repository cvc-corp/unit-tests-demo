package br.com.cvc.api.unittestsdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {

    @Id
    @Column(name = "CD_PESSOA")
    private Long id;

    @Column(name = "CD_FILIAL_CVC")
    private Long code;

    @Column(name = "ST_HABILITACAO")
    private Character enabled;

    @Column(name = "CD_ESTADO")
    private String state;

    public String getIdentifier() {
        return this.id + " - " + this.code;
    }

}