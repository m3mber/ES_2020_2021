package com.service.bustracker.unit;

import com.service.bustracker.model.DataBusInfo;
import org.junit.Assert;
import org.junit.Test;

public class DataBusInfoTest {

    @Test
    public void testDataBusInfo(){

        DataBusInfo dataBusInfo = new DataBusInfo(
                24582903,"00000000-0000-0000-0000-000000002518",
                225,24.53,"-8.610583","41.14898",12,
                "2018-10-08 00:00:00.001","2018-10-08 00:00:01.638819") ;


        Assert.assertEquals(24582903, dataBusInfo.getId());
        Assert.assertEquals(225, dataBusInfo.getLocation_id());
        Assert.assertEquals("00000000-0000-0000-0000-000000002518", dataBusInfo.getNode_id());
        Assert.assertEquals("-8.610583", dataBusInfo.getLon());

        dataBusInfo.setLocation_id(123);
        //Assert.assertEquals(225, dataBusInfo.getLocation_id());
        Assert.assertEquals(123, dataBusInfo.getLocation_id());

        dataBusInfo.setNode_id("2518");
        //Assert.assertEquals("00000000-0000-0000-0000-000000002518", dataBusInfo.getNode_id());
        Assert.assertEquals("2518", dataBusInfo.getNode_id());

        dataBusInfo.setLon("-9.610583");
        //Assert.assertEquals("-8.610583", dataBusInfo.getLon());
        Assert.assertEquals("-9.610583", dataBusInfo.getLon());

        dataBusInfo.setLat("43.14898");
        // Assert.assertEquals("-8.610583", dataBusInfo.getLat());
        Assert.assertEquals("43.14898", dataBusInfo.getLat());


    }

}
