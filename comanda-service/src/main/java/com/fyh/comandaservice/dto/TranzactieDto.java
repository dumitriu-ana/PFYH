package com.fyh.comandaservice.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TranzactieDto {
    private Long idClient;
    private Long idSpecialist;
    private Long idComanda;
    private Timestamp data;
    private BigDecimal comisionProcent;
    private BigDecimal valoare;

    public Long getIdClient() { return idClient; }
    public void setIdClient(Long idClient) { this.idClient = idClient; }
    public Long getIdSpecialist() { return idSpecialist; }
    public void setIdSpecialist(Long idSpecialist) { this.idSpecialist = idSpecialist; }
    public Long getIdComanda() { return idComanda; }
    public void setIdComanda(Long idComanda) { this.idComanda = idComanda; }
    public Timestamp getData() { return data; }
    public void setData(Timestamp data) { this.data = data; }
    public BigDecimal getComisionProcent() { return comisionProcent; }
    public void setComisionProcent(BigDecimal comisionProcent) { this.comisionProcent = comisionProcent; }
    public BigDecimal getValoare() { return valoare; }
    public void setValoare(BigDecimal valoare) { this.valoare = valoare; }
}
