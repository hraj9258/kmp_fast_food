package com.hraj9258.fastfood.di

import com.hraj9258.fastfood.auth.data.AppWriteAuthRepository
import com.hraj9258.fastfood.auth.domain.AuthRepository
import com.hraj9258.fastfood.auth.presentation.AuthViewModel
import com.hraj9258.fastfood.core.data.AppWriteFactory
import com.hraj9258.fastfood.food.SharedViewModel
import com.hraj9258.fastfood.food.search.data.repository.AppwriteFoodRepository
import com.hraj9258.fastfood.food.search.domain.FoodRepository
import com.hraj9258.fastfood.food.search.presentation.SearchViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val SharedModules = module {

    single{ AppWriteFactory.create() }

    singleOf(::AppWriteAuthRepository).bind<AuthRepository>()
    singleOf(::AppwriteFoodRepository).bind<FoodRepository>()

    viewModelOf(::AuthViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SharedViewModel)
}