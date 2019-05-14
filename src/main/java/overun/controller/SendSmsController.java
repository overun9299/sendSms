package overun.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import overun.utils.SendSmsUtil;

/**
 * @ClassName: sendSmsController
 * @Description:
 * @author: ZhangPY
 * @version: V1.0
 * @date: 2019/5/14 13:44
 * @Copyright:
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/send")
@Api(value = "测试阿里云短信发送类", tags = "测试阿里云短信发送类")
public class SendSmsController {


    @Value("${alibaba.signname}")
    private String signname;
    @Value("${alibaba.templatecode}")
    private String templatecode;
    @Value("${alibaba.accessKeyId}")
    private String accessKeyId;
    @Value("${alibaba.accessSecret}")
    private String accessSecret;
    @Value("${sendPassword}")
    private String myPassword;

    @ResponseBody
    @RequestMapping(value = "/sms" , method = RequestMethod.POST)
    @ApiOperation(value = "发送信息接口", notes = "根据输入的电话号码和名字发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "phonenumber", value = "11位手机号码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "reName", value = "接收人姓名", required = true, dataType = "String")}
    )
    public String sendSms(String phonenumber , String reName, String password) {

        if (myPassword.equals(password)) {
            if (StringUtils.isNotBlank(phonenumber) && StringUtils.isNotBlank(reName)) {
                String templateparam = SendSmsUtil.getTemplateparam(reName);
                SendSmsUtil.sendSms(phonenumber, signname, templatecode, templateparam, accessKeyId, accessSecret);
                return "发送短信成功！";
            }
        }
        return "密码错误！";
    }
}
