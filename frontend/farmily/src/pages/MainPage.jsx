import { useState, useEffect } from 'react';
import axios from '../api/axios.jsx';

import MainTree from '../components/tree/MainTree.jsx';
import Challenge from '../components/tree/Challenge.jsx';
import Board from '../components/tree/Board.jsx';

import { useDispatch } from 'react-redux';
import { getAccessToken } from '../store/auth.jsx';
import { setFamily } from '../store/family.jsx';
import { useParams } from 'react-router-dom';

export default function MainPage() {
  const dispatch = useDispatch();

  const cookies = document.cookie.split(';');
  const cookie = cookies.find((c) => c.startsWith('accessToken='));
  const accessToken = cookie.split('accessToken=')[1];

  const { familyId } = useParams();

  // 유저 정보 통해서 familyID 받아오면 수정할 것
  useEffect(() => {
    dispatch(getAccessToken({ accessToken: accessToken }));
    axios
      .get(`/family/${familyId}`)
      .then((res) => {
        const familyData = {
          id: res.data.id,
          name: res.data.name,
          motto: res.data.motto,
          tree: res.data.tree,
          challengesIds: res.data.challengesIds,
          invitationCode: res.data.invitationCode,
          sprintId: res.data.sprintId,
        };
        dispatch(setFamily(familyData));
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <>
      <Challenge />
      <MainTree />
      <Board title="나무 꾸미기" />
    </>
  );
}
