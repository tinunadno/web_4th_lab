package org.web_4th_lab.web_4th_lab.Beans;

import jakarta.ejb.Stateless;
import org.web_4th_lab.web_4th_lab.DTO.CheckPointRequest;
import org.web_4th_lab.web_4th_lab.DTO.ResultListResponse;
import org.web_4th_lab.web_4th_lab.dao.ResultDao;
import org.web_4th_lab.web_4th_lab.dao.UserDAO;
import org.web_4th_lab.web_4th_lab.Utils.DateFormer;
import org.web_4th_lab.web_4th_lab.entities.Result;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class PointService {
    UserDAO userDAO = new UserDAO();
    ResultDao resultDao = new ResultDao();
    private final DateFormer dateFormer= new DateFormer();

    public void clearPointHistory(long userID) throws IllegalArgumentException{
        resultDao.deleteResultsByUserId(userID);
    }

    public void checkPoint(CheckPointRequest checkPointRequest) throws IllegalArgumentException{
        long start_time = System.nanoTime();
        boolean result = pointBelongs(checkPointRequest.getX_cord(), checkPointRequest.getY_cord(), checkPointRequest.getRadius());
        long execution_time = System.nanoTime() - start_time;
        Result res = new Result(checkPointRequest.getX_cord(), checkPointRequest.getY_cord(), checkPointRequest.getRadius(),
                result, dateFormer.getCurrentDate(), execution_time, userDAO.getUserById(checkPointRequest.getId()));
        resultDao.saveResult(res);
    }

    public ResultListResponse getUserHistory(long userID) throws IllegalArgumentException{
        List<Result> resultHistory = resultDao.getResultsByUserId(userID);
        return new ResultListResponse(resultHistory);
    }

    private boolean pointBelongs(double x, double y, double r) {
        boolean firstFig=(y<=0 && y>=-r) && (x<=0 && x>=-r/2);
        boolean secondFig=((x*x+y*y)<=(r*r/4)) && (x>=0) && (y>=0);
        boolean thirdFig=(y>=((x-r/2)*2)) && (x>=0) && (y<=0);
        return (firstFig || secondFig ||thirdFig);
    }
}
