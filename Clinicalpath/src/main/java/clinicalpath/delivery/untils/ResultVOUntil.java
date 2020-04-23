package clinicalpath.delivery.untils;

import clinicalpath.delivery.ViewObj.ResultVO;

public class ResultVOUntil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setMessage(msg);
        resultVO.setCode(code);
        return resultVO;

    }
}
