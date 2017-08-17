package com.geely.app.geelyapprove.common.utils;

import android.content.Context;

import com.geely.app.geelyapprove.common.application.GeelyApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oliver on 2016/9/13.
 */

public class CommonValues {
    public static final String BASE_TEST_HOST = "http://118.178.89.47:8899/WorkflowService.asmx";//测试环境
//    public static final String BASE_TEST_HOST = "http://bpmservice.geely.com/WorkflowService.asmx";//生产环境
//    public static final String BASE_TEST_HOST = "http://218.75.72.105:8899/WorkflowService.asmx"; //吉利外网UAT环境

    public static String currentHost = BASE_TEST_HOST;
    public static final String WORKFLOW_BLEAVE = "BusinessTripRequest";
    public static final String WORKFLOW_REST = "LeaveRequest";
    public static final String WORKFLOW_COST = "ExpenseRequest";
    public static final String WORKFLOW_ENTER_EXPENSE = "EntertainmentExpenseRequest";
    public static final String WORKFLOW_TRAVEL_OFFER = "TravelExpenseRequest";
    public static final String WORKFLOW_PAYMENT ="PaymentRequest";
    public static final String WORKFLOW_EXTRAWORK ="OvertimeRequest";
    public static final String WORKFLOW_POSTFILE ="FileRequest";
    public static final String REQ_ADD_ATTACHMENT =currentHost+ "/AddAttachment";
    public static final String REQ_TEST_URL = currentHost + "/GeelyServlet";

    /**
     * 登陆
     */
    public static final String REQ_LOGIN_GET = currentHost + "/GetLoginUserId";

    /**
     * 获取所有员工
     * post
     */
    public static final String GET_ALL_USER = currentHost + "/GetUserSelectData";

    /**
     * 我启动的
     */
    public static final String REQ_I_LAUNCH_UNCOMMITTED = currentHost + "/GetDraftList";
    public static final String REQ_I_LAUNCH = currentHost + "/GetRequestList";

    /**
     * 我的待办
     */
    public static final String REQ_WAITING_TODO_FINSHED = currentHost + "/GetApprovalList";
    public static final String REQ_WAITING_TODO_NOT_FINSHED = currentHost + "/GetToDoList";

    /**
     * 抄送给我
     */
    public static final String REQ_CC_TO_ME = "http://";

    /**
     * 公出
     */
    //发起或保存未提交进入
    public static final String REQ_BLEAVE_APPLY_POST = currentHost + "/AddOrUpdatePostBusinessTrip";
    //审批
    public static final String REQ_BLEAVE_APPROVE_POST = currentHost + "/ApprovalPostBusinessTrip";
    //明细查看
    public static final String REQ_BLEAVE_DETAIL_DISPLAY = currentHost + "/DisplayBusinessTripRequest";

    /**
     * 加班
     */
    public static final String REQ_EXTRAWORK_APPLY = currentHost + "/AddOrUpdatePostOverTime";
    public static final String REQ_EXTRAWORK_DETAIL = currentHost + "/DisplayOverTimeRequest";
    public static final String REQ_EXTRAWORK_APPROVE = currentHost + "/ApprovalPostOverTime";

    /**
     * 请假
     */
    public static final String REQ_REST_APPLY = currentHost + "/AddOrUpdatePostLeave";
    public static final String REQ_REST_APPROVE = currentHost + "/ApprovalPostLeave";
    public static final String REQ_REST_DETAIL = currentHost + "/DisplayLeaveRequest";

    /**
     * 差旅报销
     */
    public static final String REQ_TRAVEL_EXPENSE_APPLY = currentHost + "/AddOrUpdatePostTravelExpense";
    public static final String REQ_TRAVEL_EXPENSE_APPROVE = currentHost + "/ApprovalPostTravelExpense";
    public static final String REQ_TRAVEL_EXPENSE_DETAIL = currentHost + "/DisplayTravelExpenseRequest";

    /**
     * 付款
     */
    public static final String REQ_PAYMENT_APPLY = currentHost + "/AddOrUpdatePostPayment";
    public static final String REQ_PAYMENT_APPROVE = currentHost + "/ApprovalPostPayment";
    public static final String REQ_PAYMENT_DETAIL = currentHost + "/DisplayPaymentRequest";

    /**
     * 费用
     */
    //发起或保存未提交进入
    public static final String REQ_EXPENSE_APPLY = currentHost + "/AddOrUpdatePostExpense";
    //明细查看
    public static final String REQ_EXPENSE_APPROVE = currentHost + "/DisplayExpenseRequest";
    //审批
    public static final String REQ_EXPENSE_DETAIL = currentHost + "/ApprovalPostExpense";
    /**
     * 招待费
     */
    //发起
    public static final String REQ_EXPENSEREQUEST_APPLY = currentHost + "/AddOrUpdatePostEntertainmentExpenseRequest";
    //审批
    public static final String REQ_EXPENSEREQUEST_APPROVE = currentHost + "/ApprovalPostEntertainmentExpenseRequest";
    //查看
    public static final String REQ_EXPENSEREQUEST_DETAIL = currentHost + "/DisplayEntertainmentExpenseRequest";

    /**
     * 发文
     */
    //发起
    public static final String REQ_POSTFILE_APPLY = currentHost + "/AddOrUpdatePostFile";
    //审批
    public static final String REQ_POSTFILE_APPROVE = currentHost + "/ApprovalPostFile";
    //查看
    public static final String REQ_POSTFILE_DETAIL = currentHost + "/DisplayFileRequest";

    /**
     * 通用
     */
    //查看
    public static final String UNIFIED_DETATIL = currentHost + "/DisplayAppCurrencyRequest";
    public static final String UNIFIED_APPROVE = currentHost + "/ApprovalAppCurrencyRequest";

    /**
     * 转办
     */
    public static final String FOR_WARD_TASK_PROCESS = currentHost + "/ForwardTaskToProcess";

    /**
     * 审批动作
     */
    public static final String GET_PROCESS_ACTION = currentHost + "/GetProcessAction";

    /**
     * 获取头像
     */
    public static final String GET_USER_PHOTO = currentHost + "/GetUserPhoto";
    /**
     * 下拉框
     */
    //获取请假剩余天数、小时数
    public static final String GET_LEAVE_REQUESTLEFTDAYS_ORHOURS_BYCODE = currentHost + "/GetLeaveRequestLeftDaysOrHours";
    public static final String GET_CURRENCY_LIST = currentHost + "/GetCurrencyList";
    //公司集合
    public static final String GET_COMPANY_LIST = currentHost + "/GetCompanyList";
    //省份集合
    public static final String GET_PRIVINCE_LIST = currentHost + "/GetProvienceList";
    //银行集合
    public static final String GET_PAYEEBANK_LIST = currentHost + "/GetPayeeBankList";
    //支行集合
    public static final String GET_PAYEEBANKBRANCH_LIST = currentHost + "/GetPCBBranchBankList";
    //市集合
    public static final String GET_CITY_LIST = currentHost + "/GetCityList";
    public static final String GET_COMPANYCB_LIST = currentHost + "/GetCompanyCBPList";
    //省市银行支行联动
    public static final String GET_PCBBRANCHBANK_LIST = currentHost + "/GetPCBBranchBankList";
    //下拉框
    public static final String GET_DICTIONARY_LIST = currentHost + "/GetDictionaryByCode";
    //合规管理员
    public static final String GET_GET_COMLIANCE_LIST = currentHost + "/?op=GetComlianceAdministrator";
    public static final String REQ_DELETE_DRAFT = currentHost + "/DeleteDraft";
    public static final String BASE_HOST = "http://180.97.80.232:8899/WorkflowService.asmx";
    public static final String BASE_HOST_ATTACHMENT = "http://180.97.80.232:8888/Attachments";
    public static final String DICTIONARY_TALLER = currentHost + "/GetTallerList"; //出纳人
    public static final String DICTIONARY_EXPENDITURE_TYPE = currentHost + "/GetExpenditureTypeList"; //支出类别
    //支出类别
    public static final String GET_EXPENDITURETYPE_LIST = currentHost + "/GetExpenditureTypeList";
    // 出纳
    public static final String GET_TALLER_LIST = currentHost + "/GetTallerList";
    public static final String REQ_REQUESTOR_INF = currentHost + "/GetRequestorIdInfo";
    public static final int CODE_OA_REQUEST = 2333;
    public static final int MYCOMM = 9527;
    public static final int MYLAUN = 9528;
    //获取公出关联单
    public static final String REQ_UNION_ORDERS = currentHost+"/GetCompletedOrders";
    //点击下载源文件
    public static final String CLICK_LOOK_DATA =currentHost + "/GetSingleAttachment";
    //删除附件
    public static final String DELETE_ATTACHMENT = currentHost +"/DeleteAttachment";
    //public static final String CLICK_LOOK_DATA = "http://180.97.80.232:8888/uploadFile/DownloadFile?fileUrl=";
    //根据员工编号获取银行信息
    public static final String GET_PERSONAL_ACCOUNT_INFO = currentHost + "/GetAllPersonalAccountInfo";



    /**
     * 公用请求
     */


    public static String DEVICE_TYPE = "Android";
    public static String NET;

    public static String getLocalMacAddressFromBusybox(){
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig","HWaddr");

        //如果返回的result == null，则说明网络不可取
        if(result==null){
            return "网络出错，请检查网络";
        }

        //对该行数据进行解析
        //例如：eth0      Link encap:Ethernet  HWaddr 00:16:E8:3E:DF:67
        if(result.length()>0 && result.contains("HWaddr")==true){
            Mac = result.substring(result.indexOf("HWaddr")+6, result.length()-1);

             /*if(Mac.length()>1){
                 Mac = Mac.replaceAll(" ", "");
                 result = "";
                 String[] tmp = Mac.split(":");
                 for(int i = 0;i<tmp.length;++i){
                     result +=tmp[i];
                 }
             }*/
            result = Mac;
        }
        return result;
    }

    private static String callCmd(String cmd,String filter) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader (is);

            //执行命令cmd，只取结果中含有filter的这一行
            while ((line = br.readLine ()) != null && line.contains(filter)== false) {
                //result += line;
            }
            result = line;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, Object> getCommonParams(Context context) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", GeelyApp.getLoginEntity() != null ? GeelyApp.getLoginEntity().getUserId() : "");
        param.put("deviceId",getLocalMacAddressFromBusybox());
        param.put("deviceType", "Android");
        param.put("Language",GeelyApp.getLanguage());
        int apnType = NetworkHttpManager.getAPNType(context);
        if (apnType == 1){
            param.put("net", "WIFI");
        }else if (apnType == 2){
            param.put("net", "2G");
        }else if (apnType == 3){
            param.put("net", "3G");
        }else if (apnType == 4){
            param.put("net", "4G");
        }else if (apnType == 0){
            param.put("net", "");
        }
        param.put("sign", GeelyApp.getLoginEntity() != null ? GeelyApp.getLoginEntity().getSign() : "");
        android.util.Log.e("CommonFragment", "userId " +(GeelyApp.getLoginEntity() != null ? GeelyApp.getLoginEntity().getUserId() : ""));
        android.util.Log.e("CommonFragment", "vision " + "V1.74");
        android.util.Log.e("CommonFragment", "deviceId " + getLocalMacAddressFromBusybox());
        android.util.Log.e("CommonFragment", "deviceType " + "Android");
        android.util.Log.e("CommonFragment", "net " + "wifi");
        android.util.Log.e("CommonFragment", "sign " + (GeelyApp.getLoginEntity() != null ? GeelyApp.getLoginEntity().getSign() : ""));
        return param;
    }

    public static boolean firstIn = true;

}
