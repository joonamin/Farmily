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
import { setFamily, setHarvest } from '../store/family';
import styled from 'styled-components';
import { useParams } from 'react-router-dom';

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
  const family = useSelector((state) => state.family.value);
  const [challengeData, setChallengeData] = useState({
    challenge1: null,
    challenge2: null,
    challenge3: null,
  });
  const [isChanged, setIsChanged] = useState(false);
  const [familyPoint, setFamilyPoint] = useState(0);
  const [treeFruits, setTreeFruits] = useState([
    {
      recordId: 0,
      recordTitle: '',
      position: {
        row: 0,
        col: 0,
      },
    },
  ]);
  const CalIsHarvest = (date) => {
    const endDate = date;
    const today = new Date();

    if (endDate) {
      const endDateobj = new Date(endDate);
      if (today > endDateobj) {
        dispatch(setHarvest({ needHarvest: true }));
      } else {
        dispatch(setHarvest({ needHarvest: false }));
      }
    }
  };

  const test = () => {
    dispatch(setHarvest({ needHarvest: true }));
  };

  const { familyId } = useParams();

  const handleChange = () => {
    setIsChanged(!isChanged);
  };
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
          profileDto: res.data.profileDto,
          tree: res.data.tree,
          invitationCode: res.data.invitationCode,
          challengesIds: res.data.challengesIds,
          mainSprint: res.data.mainSprint,
          point: res.data.point,
          fruitSkins: res.data.fruitSkins,
        };

        dispatch(setFamily(familyData));
        setTreeFruits(res.data.tree.mainRecordFruitDtoList);
        setFamilyPoint(res.data.point);
        CalIsHarvest(res.data.mainSprint.endDate);

        if (familyData.challengesIds && familyData.challengesIds.length > 0) {
          Promise.all(familyData.challengesIds.map((id) => axios.get(`/record/${id}`)))
            .then((responses) => {
              const today = new Date();
              const sortedChallenges = responses
                .map((res) => res.data)
                .filter((challenge) => new Date(challenge.dateRange.endDate) > today)
                .sort((a, b) => new Date(a.dateRange.endDate) - new Date(b.dateRange.endDate));

              setChallengeData({
                challenge1: sortedChallenges[0] || null,
                challenge2: sortedChallenges[1] || null,
                challenge3: sortedChallenges[2] || null,
              });
            })
            .catch((err) => console.error(err));
        } else if (familyData.challengesIds.length === 0) {
          setChallengeData({
            challenge1: null,
            challenge2: null,
            challenge3: null,
          });
        }
      })
      .catch((err) => console.error(err));
  }, [dispatch, isChanged]); // 종속성 배열에 dispatch 추가

  return (
    <Container>
      {challengeData.challenge3 ? (
        <Challenge3Styled data={challengeData.challenge3} handleChange={handleChange} />
      ) : (
        <div className="w-28 h-28 ml-5"></div> // 챌린지가 없을 때 공간을 차지하는 빈 div
      )}
      {challengeData.challenge2 ? (
        <Challenge2Styled data={challengeData.challenge2} handleChange={handleChange} />
      ) : (
        <div className="w-28 h-28 ml-5"></div>
      )}
      {challengeData.challenge1 ? (
        <Challenge1Styled data={challengeData.challenge1} handleChange={handleChange} />
      ) : (
        <div className="w-28 h-28 ml-5"></div>
      )}
      <MainTree treeFruits={treeFruits} />
      {family.needHarvest ? (
        <Harvest title="수확하기" />
      ) : (
        <Board
          title="나무 꾸미기"
          handleChange={handleChange}
          familyPoint={familyPoint}
          setFamilyPoint={setFamilyPoint}
          disabled={false}
        />
      )}
    </Container>
  );
}
