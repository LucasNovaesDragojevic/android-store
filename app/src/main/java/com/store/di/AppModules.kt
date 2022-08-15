package com.store.di

import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.store.database.AppDatabase
import com.store.database.dao.ProductDao
import com.store.model.Product
import com.store.repository.LoginRepository
import com.store.repository.PaymentRepository
import com.store.repository.ProductRepository
import com.store.ui.fragment.PaymentFragment
import com.store.ui.fragment.ProductDetailsFragment
import com.store.ui.fragment.ProductListFragment
import com.store.ui.recyclerview.adapter.ListaPagamentosAdapter
import com.store.ui.recyclerview.adapter.ProductAdapter
import com.store.ui.viewmodel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.math.BigDecimal

private const val DATABASE_NAME = "store.db"
private const val DATABASE_TEST_NAME = "store-test.db"

val testeDatabaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_TEST_NAME).fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao: ProductDao by inject()
                        dao.save(
                            Product(
                                name = "Bola de futebol",
                                price = BigDecimal("100")
                            ), Product(
                                name = "Camisa",
                                price = BigDecimal("80")
                            ),
                            Product(
                                name = "Chuteira",
                                price = BigDecimal("120")
                            ), Product(
                                name = "Bermuda",
                                price = BigDecimal("60")
                            )
                        )
                    }
                }
            }).build()
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME).build()
    }
}

val preferencesModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val daoModule = module {
    single { get<AppDatabase>().productDao() }
    single { get<AppDatabase>().paymentDao() }
    single { ProductRepository(get()) }
    single { PaymentRepository(get()) }
    single { LoginRepository(get()) }
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
    viewModel { (id: Long) -> ProductDetailsViewModel(id, get()) }
    viewModel { PaymentViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel() }
}