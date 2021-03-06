<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link href="/assets/plugins/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script src="/assets/plugins/jquery-3.1.1/jquery-3.1.1.min.js" type="text/javascript"></script>
    <link href="/assets/plugins/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="/assets/plugins/bootstrap-3.3.7/css/bootstrap-theme.min.css" type="text/css" rel="stylesheet" />
    <script src="/assets/plugins/bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="/assets/plugins/font-awesome-4.7.0/css/font-awesome.min.css" type="text/css" rel="stylesheet" />
    <link href="/assets/plugins/bootstrap-dialog/css/bootstrap-dialog.min.css" type="text/css" rel="stylesheet"/>
    <script src="/assets/plugins/bootstrap-dialog/js/bootstrap-dialog.min.js" type="text/javascript"></script>
    <link href="/assets/plugins/icheck-1.x/skins/minimal/blue.css" rel="stylesheet" type="text/css" />
    <script src="/assets/plugins/icheck-1.x/icheck.min.js"></script>

    <link href="/assets/css/login.css" rel="stylesheet" type="text/css" /><!--登录-->

    <#--<object classid="clsid:A318A9AC-E75F-424C-9364-6B40A848FC6B" width="0" height="0" id="zkonline"></object>-->
    <#--<comment>-->
        <#--<EMBED type="application/x-eskerplus"-->
               <#--classid="clsid:A318A9AC-E75F-424C-9364-6B40A848FC6B"-->
               <#--codebase="ZKOnline.ocx"-->
               <#--width=0 height=0>-->
        <#--</EMBED>-->
    <#--</comment>-->
</head>
<body class="loginBox">
<div class="loginBox">
    <div class="login-left">
        <img id="columnarDisc" src="/assets/images/login1_03.png" alt="">
        <img id="light_big" src="/assets/images/light_big_03.png" alt="">
        <img id="light_small" src="/assets/images/light_small_03.png" alt="">
        <img id="line1" src="/assets/images/login2_03.png" alt="">
        <img id="line2" src="/assets/images/login3_03.png" alt="">
        <img id="line3" src="/assets/images/login4_03.png" alt="">
        <img id="line4" src="/assets/images/login5_03.png" alt="">
    </div>
    <div class="login-information">
        <div class="top">
            <div class="font">
                基层舆情上报系统
            </div>
            <!--指纹入口点 fingerPrintBtn-->
            <a id="fingerPrintBtn" href="javascript:void(0)"><img  class="fingerPrint" src="/assets/images/fingerPrint_03.png" alt=""></a>
        </div>
        <div class="login">
            <form action="" id="accountsForm">
                <div class="accounts">
                    <span class="glyphicon glyphicon-user"></span>
                    <input type="text" placeholder="帐号" maxlength="100" id="txt_username" value="" />
                </div>
                <div class="password">
                    <span class="glyphicon glyphicon-lock"></span>
                    <input type="password" placeholder="密码" maxlength="100" id="txt_password" value="" onkeydown=""/>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="txt_rememberMe" name="iCheck"  /> 记住密码
                    </label>
                </div>
                <input type="button" class="btn btn-default btn-info login-btn" onclick="" value="登录" />
            </form>
            <!--指纹-->
            <div id="fingerPrintDiv">
                <h5 class="text-center tip">请水平按压手指验证</h5>
                <div class="fingerPrints">
                    <canvas id="canvasComp" width="145" height="145"
                            style=""></canvas>
                    <input type="hidden" id="fingerprint_val">
                </div>
                <input type="button" class="btn btn-default btn-info fingerprintLogin-btn" onclick="" value="指纹登录" />
            </div>
            <a class="pull-right loadBtn" href="/fingerprint/ZKBIOOnline">下载驱动</a>
        </div>
    </div>
</div>

<div id="fingerprintRegister" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header logocbg">
                <h5 class="modal-title colorwhite">初始化用户指纹</h5>
            </div>
            <div class="modal-body  text-center">
                <div class="form-horizontal Margin">
                    <div class="form-body">
                        <div class="form-group">
                            注意：只保存录入额第一个指纹
                        </div>
                        <canvas id="canvas" width="430" height="380"
                                style="background: rgb(243, 245, 240)"></canvas>
                        <input type="hidden" id="userIdM">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="col-sm-12">
                    <button type="button" class="saveBtn">确认</button>
                    <button type="button" class="" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "/public/footer.ftl"/>
<script type="text/javascript" src="/assets/fingerprint/js/main.js"></script>
<script type="text/javascript" src="/assets/fingerprint/js/fingerprint.js"></script>
<script type="text/javascript" src="/assets/fingerprint/js/baseMoth.js"></script>
<script type="text/javascript" src="/assets/fingerprint/js/dhtmlxCommon.js"></script>
<script src="/common/utils.js"></script>
<#--<script src="/common/finger.js"></script>-->
<script src="/common/login.js"></script>
</body>
</html>