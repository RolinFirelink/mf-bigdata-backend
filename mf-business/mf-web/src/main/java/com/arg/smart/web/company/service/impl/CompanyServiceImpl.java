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
import java.util.*;
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
    public List<Company> list(ReqCompany reqCompany) {
        if (reqCompany == null) {
            return this.list();
        }
        LambdaQueryWrapper<Company> companyQueryWrapper = new LambdaQueryWrapper<>();
        Integer companyType = reqCompany.getCompanyType();
        String companyName = reqCompany.getCompanyName();
        String contacts = reqCompany.getContacts();
        String city = reqCompany.getCity();
        String businessScope = reqCompany.getBusinessScope();
        String contactPhone = reqCompany.getContactPhone();
        String province = reqCompany.getProvince();
        String region = reqCompany.getRegion();
        String address = reqCompany.getAddress();
        if (companyType != null && companyType != 0) {
            companyQueryWrapper.eq(Company::getCompanyType, companyType);
        }
        if (companyName != null && !companyName.equals("")) {
            companyQueryWrapper.like(Company::getCompanyName, companyName);
        }
        if (contacts != null && !contacts.equals("")) {
            companyQueryWrapper.like(Company::getContacts, contacts);
        }
        if (businessScope != null && !businessScope.equals("")) {
            companyQueryWrapper.like(Company::getBusinessScope, businessScope);
        }
        if (contactPhone != null && !Objects.equals(contacts, "")) {
            companyQueryWrapper.like(Company::getContactPhone, contactPhone);
        }
        if (province != null && !province.equals("")) {
            companyQueryWrapper.like(Company::getAddress, province);
        }
        if (city != null && !city.equals("")) {
            companyQueryWrapper.like(Company::getAddress, city);
        }
        if (region != null && !region.equals("")) {
            companyQueryWrapper.like(Company::getAddress, region);
        }
        if (address != null && !address.equals("")) {
            companyQueryWrapper.like(Company::getAddress, address);
        }
        return this.list(companyQueryWrapper);
    }

    @Override
    public List<Company> getOptionsByCompanyType(Integer companyType) {

        LambdaQueryWrapper<Company> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Company::getCompanyType, companyType)
                .select(Company::getId, Company::getCompanyName);
        return this.list(queryWrapper);

    }

    @Override
    public Map<String, List<CompanyVO>> getCompanyVOByCity(Integer flag, String cityName) {
        Map<String, List<CompanyVO>> outMap = new HashMap<>();
        // 获取给定城市的区县列表
        List<String> districts = productBaseMapper.getDistrict(cityName);
        // 遍历区县列表
        for (String district : districts) {
            // 格式化区县名称
            String formattedDistrict = getDistrict(district);
            if (!formattedDistrict.equals("")) {
                List<CompanyVO> companyVOs = getCompanyVO(formattedDistrict, flag, cityName);
                //添加
                outMap.put(formattedDistrict, companyVOs);
            }

        }
        return outMap;
    }

    @Override
    public boolean saveCompany(Company company) {
        if (setLocation(company)) {
            return this.save(company);
        }
        return false;
    }

    @Override
    public boolean updateCompanyById(Company company) {
        if (setLocation(company)) {
            return this.updateById(company);
        }
        return false;
    }

    //设置为public可在EXCEL导入使用
    public boolean setLocation(Company company) {
        String detail = company.getAddress();
        if (company.getAreaCode() != null) {
            //查询地址
            String address = this.baseMapper.getPidsName(company.getAreaCode());
            if (address != null) {
                //地址处理
                String[] split = address.split("\\.");
                //判断省份城市和区是否有设置
                if (split.length >= 2) {
                    //存在城市
                    company.setProvince(split[1]);
                } else {
                    company.setProvince(null);
                }
                if (split.length >= 3) {
                    //存在城市
                    company.setCity(split[2]);
                } else {
                    company.setCity(null);
                }
                //判断区是否有设置
                if (split.length >= 4) {
                    //存在城市
                    company.setRegion(split[3]);
                } else {
                    company.setRegion(null);
                }
                address = address.replace(".", "");
                //如果有详细地址则设置详细地址
                if (detail != null) {
                    address = address + detail;
                }
                //设置地址
                company.setAddress(address);
            } else {
                //areaCode错误或者数据库无该数据
                return false;
            }
        }
        return true;
    }

    private String getDistrict(String district) {
        String[] segments = district.split("\\.");
        if (segments.length >= 4) {
            return segments[3];
        } else {
            return "";
        }
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
        // 获取区号对应的经纬度
        Map<String, double[]> coordinatesMap = getCoordinatesByAreaCodes(areaCodes);

        for (Company company : list) {
            // 获取对应公司的产品基本信息
            ProductBase productBase = productBaseMapper.selectOne(new QueryWrapper<ProductBase>()
                    .eq("flag", flag)
                    .eq("company_id", company.getId()));

            // 获取公司所在位置的经纬度
            double[] coordinates = coordinatesMap.get(company.getAreaCode());
            double latitude = coordinates[0];
            double longitude = coordinates[1];
            //设置
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
