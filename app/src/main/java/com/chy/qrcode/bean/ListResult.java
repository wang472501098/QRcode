package com.chy.qrcode.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 类名称：com.publicnum.commission.bean
 * 类描述：
 * 创建人：yd_10
 * 创建时间：2017/5/10 9:35
 * 修改人：
 * 修改时间：2017/5/10 9:35
 * 修改备注：
 */
public class ListResult<T> implements Serializable {

    /**
     * res : 1
     * data : [{"name":"17侃侃","logoUrl":"https://mp.weixin.qq.com/misc/getheadimg?token=1164090841&fakeid=3215704505&r=104472","qrCode":"data:image/png;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCAGuAa4DASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9U6KKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiuR+KPxR8M/BjwJqfjHxlqY0bw5ppi+1Xv2eWfy/MlSJPkiVnOXkQcKcZyeATQB11FfKn/D0b9mL/opv/lA1T/5Go/4ejfsxf9FN/wDKBqn/AMjUAfVdFfKn/D0b9mL/AKKb/wCUDVP/AJGo/wCHo37MX/RTf/KBqn/yNQB9V0V8qf8AD0b9mL/opv8A5QNU/wDkaj/h6N+zF/0U3/ygap/8jUAfVdFfKn/D0b9mL/opv/lA1T/5Go/4ejfsxf8ARTf/ACgap/8AI1AH1XRXlHwM/ai+GP7Sf9t/8K48Tf8ACR/2L5H2/wD0C6tfJ87zPK/18Sbs+VJ93ONvOMjPU/FH4o+Gfgx4E1Pxj4y1MaN4c00xfar37PLP5fmSpEnyRKznLyIOFOM5PAJoA66ivlT/AIejfsxf9FN/8oGqf/I1H/D0b9mL/opv/lA1T/5GoA+q6K+VP+Ho37MX/RTf/KBqn/yNXqnwM/ai+GP7Sf8Abf","content":"心灵鸡汤 好玩的各种星座测试 小游戏等，没事乐一乐！"}]
     */

    public int res;
    @SerializedName("data")
    public List<T> data;

    @Override
    public String toString() {
        return "ListResult{" +
                "res=" + res +
                ", data=" + data +
                '}';
    }
}
