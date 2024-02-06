import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  value: {
    needHarvest: 'false',
  },
};

const harvestSlice = createSlice({
  name: 'harvest',
  initialState,
  reducers: {
    setNeedHarvest: (state, action) => {
      state.value.needHarvest = action.payload.needHarvest;
    },
  },
});

export const { setNeedHarvest } = harvestSlice.actions;
export default harvestSlice;
