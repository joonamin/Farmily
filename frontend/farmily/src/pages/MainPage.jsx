import { useState, useEffect } from 'react';
import axios from '../api/axios';
import MainTree from '../components/tree/MainTree';
import Challenge1 from '../components/tree/Challenge1';
import Challenge2 from '../components/tree/Challenge2';
import Challenge3 from '../components/tree/Challenge3';
import Board from '../components/tree/Board';
import Harvest from '../components/tree/Harvest';
import { useDispatch, useSelector } from 'react-redux';
import { getAccessToken } from '../store/auth';
import { setFamily } from '../store/family';
import styled from 'styled-components';
import { useParams } from 'react-router-dom';

// 테스트 코드
import { setNeedHarvest } from '../store/harvest.jsx';

const MainPageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ChallengesContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px; /* 컴포넌트들 사이의 간격 조정 */
`;

const Challenge1Styled = styled(Challenge1)`
  margin-bottom: 20px;
`;

const Challenge2Styled = styled(Challenge2)`
  margin-bottom: 20px;
`;

const Challenge3Styled = styled(Challenge3)`
  margin-bottom: 20px;
`;

const Container = styled.div`
  display: flex;
  align-items: flex-end; // 아이템들을 오른쪽으로 정렬
`;

export default function MainPage() {
  const dispatch = useDispatch();
  const [challengeData, setChallengeData] = useState({
    challenge1: null,
    challenge2: null,
    challenge3: null,
  });

  const harvest = useSelector((state) => state.harvest.value);
  console.log(harvest);

  const test = () => {
    dispatch(setNeedHarvest({ needHarvest: true }));
    console.log(harvest);
  };

  const { familyId } = useParams();

  useEffect(() => {
    const cookies = document.cookie.split(';');
    const cookie = cookies.find((c) => c.trim().startsWith('accessToken='));
    if (!cookie) return; // accessToken 쿠키가 없으면 초기화 중단
    const accessToken = cookie.split('=')[1];

    dispatch(getAccessToken({ accessToken }));
    axios
      .get(`/family/${familyId}`)
      .then((res) => {
        const familyData = {
          id: res.data.id,
          name: res.data.name,
          motto: res.data.motto,
          tree: res.data.tree,
          challengesIds: res.data.challengesIds,
          sprintId: res.data.sprintId,
        };
        dispatch(setFamily(familyData));

        if (familyData.challengesIds && familyData.challengesIds.length > 0) {
          Promise.all(
            familyData.challengesIds.map((id) => axios.get(`/record/${id}`))
          )
            .then((responses) => {
              const today = new Date();
              const sortedChallenges = responses
                .map((res) => res.data)
                .filter(
                  (challenge) => new Date(challenge.dateRange.endDate) > today
                )
                .sort(
                  (a, b) =>
                    new Date(a.dateRange.endDate) -
                    new Date(b.dateRange.endDate)
                );

              setChallengeData({
                challenge1: sortedChallenges[0] || null,
                challenge2: sortedChallenges[1] || null,
                challenge3: sortedChallenges[2] || null,
              });
            })
            .catch((err) => console.error(err));
        }
      })
      .catch((err) => console.error(err));
  }, [dispatch]); // 종속성 배열에 dispatch 추가

  return (
    <Container>
      {challengeData.challenge3 && (
        <Challenge3Styled data={challengeData.challenge3} />
      )}
      {challengeData.challenge2 && (
        <Challenge2Styled data={challengeData.challenge2} />
      )}
      {challengeData.challenge1 && (
        <Challenge1Styled data={challengeData.challenge1} />
      )}
      <MainTree />
      {harvest.needHarvest ? (
        <Harvest title="수확하기" />
      ) : (
        <Board title="나무 꾸미기" />
      )}
      <button onClick={test}>테스트버튼</button>
    </Container>
  );
}
