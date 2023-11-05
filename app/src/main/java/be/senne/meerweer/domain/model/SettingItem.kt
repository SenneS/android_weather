package be.senne.meerweer.domain.model

import androidx.datastore.preferences.core.Preferences

data class SettingItem<K, T>(
    val key : Preferences.Key<K>,
    val default : T,
    val serialize : (T) -> K,
    val deserialize : (K) -> T
)