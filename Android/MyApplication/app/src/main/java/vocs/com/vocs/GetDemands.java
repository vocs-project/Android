package vocs.com.vocs;

import java.util.List;

/**
 * Created by ASUS on 07/11/2017.
 */

public class GetDemands {

    private List<DemandSendReceive > demandSend;

    public List<DemandSendReceive> getDemandSend() {
        return demandSend;
    }

    public void setDemandSend(List<DemandSendReceive> demandSend) {
        this.demandSend = demandSend;
    }

    public List<DemandSendReceive> getDemandReceive() {
        return demandReceive;
    }

    public void setDemandReceive(List<DemandSendReceive> demandReceive) {
        this.demandReceive = demandReceive;
    }

    private List<DemandSendReceive > demandReceive;
}
