package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.company.req.ReqCompany;
import com.arg.smart.web.company.service.CompanyService;
import com.arg.smart.web.company.vo.CompanyVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lbz
 * @description: 企业、供货商、销售商和承运商
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    @Resource
    private ProductBaseMapper productBaseMapper;
    @Override
    public List<Company> SelectListByCondition(ReqCompany reqCompany) {
        if(reqCompany == null){
            return this.list();
        }
        QueryWrapper<Company> companyQueryWrapper = new QueryWrapper<>();
        Integer companyType = reqCompany.getCompanyType();
        String companyName = reqCompany.getCompanyName();
        if(companyType != null && companyType != 0){
            companyQueryWrapper.eq("company_type",companyType);
        }
        if(companyName != null){
            companyQueryWrapper.like("company_name",companyName);
        }
        return this.list(companyQueryWrapper);
    }

    @Override
    public List<Company> getOptionsByCompanyType(Integer companyType) {

        LambdaQueryWrapper<Company> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Company::getCompanyType,companyType)
                .select(Company::getId,Company::getCompanyName);
        return this.list(queryWrapper);

    }

    @Override
    public Map<String, List<CompanyVO>> getCompanyVOByCity(Integer flag, String cityName) {
        Map<String, List<CompanyVO>> outMap = new HashMap<>();
        if (cityName.equals("广州")) {
            outMap.put("从化", getCompanyVO("从化", flag, cityName));
        }
        return outMap;
    }

    private List<CompanyVO> getCompanyVO(String district, Integer flag, String cityName) {
        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("address", cityName + district);
        List<Company> list = this.list(queryWrapper);

        List<String> areaCodes = new ArrayList<>();
        for (Company company : list) {
            areaCodes.add(company.getAreaCode());
        }

        List<CompanyVO> companyVOList = new ArrayList<>();
        Map<String, double[]> coordinatesMap = getCoordinatesByAreaCodes(areaCodes);
        for (Company company : list) {
            ProductBase productBase = productBaseMapper.selectOne(new QueryWrapper<ProductBase>()
                    .eq("flag", flag)
                    .eq("company_id", company.getId()));

            double[] coordinates = coordinatesMap.get(company.getAreaCode());
            double latitude = coordinates[0];
            double longitude = coordinates[1];

            CompanyVO companyVO = new CompanyVO();
            companyVO.setCompanyName(company.getCompanyName());
            companyVO.setContact(company.getContactPhone());
            companyVO.setDomicile(district);
            companyVO.setAnnualOutput(productBase.getAnnualOutput());
            companyVO.setMainProduct(productBase.getMainProduct());
            companyVO.setLatLongEtude(new double[]{latitude, longitude});
            companyVOList.add(companyVO);
        }
        return companyVOList;
    }

    private Map<String, double[]> getCoordinatesByAreaCodes(List<String> areaCodes) {
        Map<String, double[]> coordinatesMap = new HashMap<>();
        for (String areaCode : areaCodes) {
            double latitude = productBaseMapper.getLatitudeByAreaCode(Long.valueOf(areaCode));
            double longitude = productBaseMapper.getLongitudeByAreaCode(Long.valueOf(areaCode));
            coordinatesMap.put(areaCode, new double[]{latitude, longitude});
        }
        return coordinatesMap;
    }
}
