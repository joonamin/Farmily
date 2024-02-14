import { Link } from 'react-router-dom';

import communityTree from '../../assets/images/communityTree.png';

export default function CommunityItem(community) {
  return (
    // 이미지, 가족명, 가족아이디, 자랑하기 내용 받아오기
    <div className="p-5 snap-center">
      <Link to={`${community.id}`}>
        <div className="bg-white shadow-md border border-gray-200 rounded-lg max-w-sm dark:bg-gray-800 dark:border-gray-700 p-3">
          <img
            className="rounded-lg"
            src={community.image.location}
            alt="tree"
          />
          <h3 className="text-lg mt-2">{community.title}</h3>
          <p>작성자 : {community.author}</p>
        </div>
      </Link>
    </div>
  );
}
