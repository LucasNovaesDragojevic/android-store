package com.store.di

import androidx.preference.PreferenceManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.store.repository.FirebaseAuthRepository
import com.store.repository.PaymentRepository
import com.store.repository.ProductRepository
import com.store.ui.fragment.PaymentFragment
import com.store.ui.fragment.ProductDetailsFragment
import com.store.ui.fragment.ProductListFragment
import com.store.ui.recyclerview.adapter.ListaPagamentosAdapter
import com.store.ui.recyclerview.adapter.ProductAdapter
import com.store.ui.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val preferencesModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val daoModule = module {
    single { ProductRepository(get()) }
    single { PaymentRepository() }
    single { FirebaseAuthRepository(get()) }
}

val uiModule = module {
    factory { ProductDetailsFragment() }
    factory { ProductListFragment() }
    factory { PaymentFragment() }
    factory { ProductAdapter(get()) }
    factory { ListaPagamentosAdapter(get()) }
}

val viewModelModule = module {
    viewModel { ProductViewModel(get()) }
    viewModel { (id: String) -> ProductDetailsViewModel(id, get()) }
    viewModel { PaymentViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { UserRegistrationViewModel(get()) }
    viewModel { AccountViewModel(get()) }
    viewModel { FormProductViewModel(get()) }
}

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore}
}