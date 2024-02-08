import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import axios from '../api/axios.jsx';
import fruitImages from '../api/fruitImages.jsx';
import ArticleList from '../components/common/ArticleList.jsx';
import ImageList from '../components/common/ImageList.jsx';
import SmallButton from '../components/button/SmallButton.jsx';

export default function RecordPage() {
  const family = useSelector((state) => state.family.value);
  const memory = useSelector((state) => state.memory.value);
  const { sprintId } = useParams();
  const [sprintDate, setSprintDate] = useState({
    startDate: '',
    endDate: '',
  });
  const [count, setCount] = useState({
    daily: 0,
    challenge: 0,
    event: 0,
  });

  // 현재 sprintId 와 페이지 sprintId가 같을때만 글쓰기 버튼 존재
  const [isValid, setIsValid] = useState(false);

  useEffect(() => {
    if (family.mainSprint.sprintId === parseInt(sprintId)) {
      setIsValid(true);
    }

    axios
      .get(`/sprint/${sprintId}/record?pageSize=8`)
      .then((res) => {
        setCount(res.data.counts);
        setSprintDate(res.data.dateRange);
      })
      .catch((err) => {
        console.error(err);
      });
  }, [family.mainSprint.sprintId, sprintId, isValid]);

  return (
    <>
      <div className="flex h-8 justify-between px-10">
        <div className="my-auto">
          {family.mainSprint.sprintId === parseInt(sprintId) ? (
            <p className="my-auto text-2xl">
              {family.mainSprint.endDate.slice(0, 4)}년{' '}
              {family.mainSprint.endDate.slice(5, 7)}월
            </p>
          ) : (
            <p className="my-auto text-2xl">
              {sprintDate.endDate.slice(0, 4)}년{' '}
              {sprintDate.endDate.slice(5, 7)}월
            </p>
          )}
        </div>
        <div className="flex h-8 my-auto">
          <span className="flex my-auto mr-5">
            <img
              className="h-8 mr-3"
              src={fruitImages[family.fruitSkins.daily]}
              alt=""
            />{' '}
            <p className="my-auto text-xl mr-1">X</p>
            <p className="my-auto text-xl">{count.daily}</p>
          </span>
          <span className="flex my-auto mr-5">
            <img
              className="h-8 mr-3"
              src={fruitImages[family.fruitSkins.event]}
              alt=""
            />{' '}
            <p className="my-auto text-xl mr-1">X</p>
            <p className="my-auto text-xl">{count.event}</p>
          </span>
          <span className="flex my-auto">
            <img
              className="h-8 mr-3"
              src={fruitImages[family.fruitSkins.challenge]}
              alt=""
            />{' '}
            <p className="my-auto text-xl mr-1">X</p>
            <p className="my-auto text-xl">{count.challenge}</p>
          </span>
        </div>
      </div>

      <ImageList />
      <ArticleList sprintId={sprintId} />
      {/* 현재 진행중 sprintId와 같은 경우에만 보이게 수정하기 */}
      {isValid && (
        <div className="flex justify-end top-0 right-0">
          <SmallButton text="글쓰기" url="/family/record/create" />
        </div>
      )}
    </>
  );
}
