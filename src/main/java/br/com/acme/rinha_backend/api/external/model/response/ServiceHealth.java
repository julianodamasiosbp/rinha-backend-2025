package br.com.acme.rinha_backend.api.external.model.response;

public class ServiceHealth {

    private boolean failing;

    private Integer minResponseTime;

    public boolean isFailing() {
        return failing;
    }

    public void setFailing(boolean failing) {
        this.failing = failing;
    }

    public Integer getMinResponseTime() {
        return minResponseTime;
    }

    public void setMinResponseTime(Integer minResponseTime) {
        this.minResponseTime = minResponseTime;
    }

}
