import { useState, useEffect, useRef } from 'react';
import CommunityItem from '../components/common/CommunityItem.jsx';
import SmallButton from '../components/button/SmallButton.jsx';
import axios from '../api/axios.jsx';

export default function CommunityPage() {
  const [posts, setPosts] = useState([]);
  const [hasNext, setHasNext] = useState(true);
  const [page, setPage] = useState(0);

  const observerRef = useRef(null);

  const handleIntersection = (entries) => {
    if (entries[0].isIntersecting) {
      // Intersection 발생 시 다음 페이지 데이터 로딩
      loadNextPage();
    }
  };

  const loadNextPage = () => {
    if (hasNext) {
      // 다음 페이지로 이동
      setPage((prevPage) => prevPage + 1);
    }
  };

  useEffect(() => {
    // 초기 데이터 로딩
    axios
      .get(`/community?reqPageNum=${page}`)
      .then((res) => {
        setPosts(res.data.contents);
        setHasNext(res.data.hasNext);
      })
      .catch((err) => {
        console.log(err);
      });

    // Intersection Observer 생성
    observerRef.current = new IntersectionObserver(handleIntersection, {
      root: null, // 기본값은 viewport
      rootMargin: '0px',
      threshold: 0.1, // 엘리먼트가 화면에 10% 이상 나타나면 콜백 실행
    });

    // Intersection Observer에 관찰 대상 추가
    observerRef.current.observe(document.getElementById('scroll-trigger'));

    // 컴포넌트가 언마운트될 때 Intersection Observer 해제
    return () => {
      observerRef.current.disconnect();
    };
  }, []); // eslint-disable-line react-hooks/exhaustive-deps

  useEffect(() => {
    // 스크롤이 끝에 도달했을 때 추가 페이지 데이터를 불러와서 기존 데이터에 추가
    if (page > 0) {
      axios
        .get(`/community?reqPageNum=${page}`)
        .then((res) => {
          setPosts((prevPosts) => [...prevPosts, ...res.data.contents]);
          setHasNext(res.data.hasNext);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [page]);

  return (
    <div className="h-full overflow-hidden p-5">
      <div className="h-5/6 flex flex-wrap justify-center snap-y overflow-y-scroll">
        {posts.map((item, index) => (
          <CommunityItem key={index} {...item} />
        ))}
        <div id="scroll-trigger" style={{ height: '10px' }}></div>
        {/* Intersection Observer의 관찰 대상으로 사용되는 엘리먼트 */}
      </div>
      <div className="flex justify-end pr-4 pt-4">
        <SmallButton text="글쓰기" url="/family/community/write" />
      </div>
    </div>
  );
}
