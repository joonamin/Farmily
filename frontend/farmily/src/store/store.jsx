import { configureStore } from '@reduxjs/toolkit';
import storage from 'redux-persist/lib/storage';
import { combineReducers } from '@reduxjs/toolkit';
import { persistReducer } from 'redux-persist';

import authSlice from './auth.jsx';
import familySlice from './family.jsx';
import userSlice from './user.jsx';

const reducers = combineReducers({
  auth: authSlice.reducer,
  family: familySlice.reducer,
  user: userSlice.reducer,
});

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['auth', 'family', 'user'],
};

const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }),
});

export default store;
