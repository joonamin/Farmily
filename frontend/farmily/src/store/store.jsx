import { configureStore } from '@reduxjs/toolkit';
import storage from 'redux-persist/lib/storage';
import { combineReducers } from '@reduxjs/toolkit';
import { persistReducer, persistStore } from 'redux-persist';

import authSlice from './auth.jsx';
import familySlice from './family.jsx';
import userSlice from './user.jsx';
import harvestSlice from './harvest.jsx';
import memorySlice from './memory.jsx';
import axios from '../api/axios.jsx';

const reducers = combineReducers({
  auth: authSlice.reducer,
  family: familySlice.reducer,
  user: userSlice.reducer,
  harvest: harvestSlice.reducer,
  memory: memorySlice.reducer,
});

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['auth', 'family', 'user', 'harvest', 'memory'],
};

const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }),
});
const persistor = persistStore(store);

// Redux Persist 이벤트 감지 및 처리
persistor.subscribe(() => {
  const state = store.getState();
  const auth = state.auth;
  const accessToken = `Bearer ${auth?.value.accessToken}`;
  axios.defaults.headers.common['Authorization'] = accessToken;
});

export { store, persistor };
