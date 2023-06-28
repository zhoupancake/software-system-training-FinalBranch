package com.systemtraining.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.systemtraining.entity.Option;
import com.systemtraining.mapper.OptionMapper;
import com.systemtraining.service.OptionService;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option> implements OptionService {
}
