import { useState, useEffect } from 'react';
import ArticleItem from './ArticleItem.jsx';
import ChallengeItem from './ChallengeItem.jsx';
import useDidMountEffect from './UseDidMountEffect.jsx';
import axios from '../../api/axios.jsx';

export default function ArticleList({ sprintId }) {
  const [records, setRecords] = useState([
    {
      id: 0,
      type: '',
      title: '',
      content: '',
      createdAt: '',
      author: { nickname: '' },
    },
  ]);
  const [challenge, setChallenge] = useState([
    {
      id: 0,
      type: '',
      title: '',
      content: '',
      createdAt: '',
      author: { nickname: '' },
    },
  ]);
  const [images, setImages] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    // 유저 인증 후 sprintId 적절히 넣기
    axios
      .get(`/sprint/${sprintId}/record?pageSize=8`)
      .then((response) => {
        setRecords(response.data.page.records);
        setChallenge(response.data.page.challenges);
        setTotalPages(response.data.page.pageTotal);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [sprintId]);

  useDidMountEffect(() => {
    axios
      .get(`/sprint/${sprintId}/record/${page}?pageSize=8`)
      .then((res) => {
        setRecords(res.data.records);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [page]);

  const handlePrevPage = () => {
    if (page > 0) {
      setPage((prevPage) => prevPage - 1);
    }
  };

  const handleNextPage = () => {
    if (page < totalPages) {
      setPage((prevPage) => prevPage + 1);
    }
  };

  const handlePageClick = (pageNumber) => {
    setPage(pageNumber);
  };

  return (
    <>
      {records.length >= 1 ? (
        <div className="px-5 h-2/5 relative mb-4">
          <table className="table-fixed w-full">
            <thead className="border-b-4 border-gray-500">
              {/* 테이블 헤더 부분 */}
            </thead>
            <tbody>
              {challenge.map((item, index) => (
                <ChallengeItem
                  key={index}
                  title={item.title}
                  nickname={item.author.nickname}
                  content={item.content}
                  createdAt={item.createdAt}
                  id={item.id}
                  type={item.type}
                />
              ))}
              {records.map((item, index) => (
                <ArticleItem
                  key={index}
                  title={item.title}
                  nickname={item.author.nickname}
                  content={item.content}
                  createdAt={item.createdAt}
                  id={item.id}
                  type={item.type}
                />
              ))}
            </tbody>
          </table>

          <span className="flex justify-center">
            <div className="flex justify-between mt-4">
              <button
                className={`bg-green-400 text-white px-4 py-2 mr-4 rounded ${
                  page === 1 ? 'disabled bg-stone-700' : ''
                }`}
                onClick={handlePrevPage}
                disabled={page === 1}
              >
                이전
              </button>
              {[...Array(totalPages).keys()].map((pageNumber) => (
                <button
                  key={pageNumber}
                  className={`${
                    pageNumber + 1 === page
                      ? 'bg-gray-500 text-white'
                      : 'bg-gray-300'
                  } px-4 py-2 rounded`}
                  onClick={() => handlePageClick(pageNumber + 1)}
                >
                  {pageNumber + 1}
                </button>
              ))}
              <button
                className={`bg-green-400 text-white px-4 py-2 ml-4 rounded ${
                  page === totalPages ? 'disabled bg-stone-700' : ''
                }`}
                onClick={handleNextPage}
                disabled={page === totalPages}
              >
                다음
              </button>
            </div>
          </span>
        </div>
      ) : (
        <div className="h-2/5 flex justify-center">
          <p className="text-2xl my-auto">가족과 함께한 추억을 작성하세요.</p>
        </div>
      )}
    </>
  );
}
