import { useState, useEffect } from 'react';
import CommunityItem from '../components/common/CommunityItem.jsx';
import SmallButton from '../components/button/SmallButton.jsx';
import axios from '../api/axios.jsx';

export default function CommunityPage() {
  const [posts, setPosts] = useState([]);
  const [hasNext, setHasNext] = useState(false);
  const [page, setPage] = useState(0);
  const [time, setTime] = useState(0);

  useEffect(() => {
    axios
      .get(`/community?reqPageNum=${page}`)
      .then((res) => {
        console.log(res);
        setPosts(res.data.contents);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div className="h-full overflow-hidden p-5">
      <div className=" h-5/6 flex flex-wrap justify-center snap-y overflow-y-scroll">
        {/* sprint 정보 보내기 */}
        {posts.map((item, index) => (
          <CommunityItem key={index} {...item} />
        ))}
      </div>
      <div className="flex justify-end pr-4 pt-4">
        <SmallButton text="글쓰기" url="/family/community/write" />
      </div>
    </div>
  );
}
