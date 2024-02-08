import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  value: {
    id: 0,
    range: {
      startDate: '',
      endDate: '',
    },
  },
};

const memorySlice = createSlice({
  name: 'memory',
  initialState,
  reducers: {
    setMemory: (state, action) => {
      state.value.id = action.payload.id;
      state.value.range = action.payload.range;
    },
  },
});

export const { setMemory } = memorySlice.actions;
export default memorySlice;
