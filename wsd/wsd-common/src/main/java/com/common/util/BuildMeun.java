package com.common.util;

import com.common.model.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 构建左侧导航树工具类
 */
public class BuildMeun {
    public static <T> Menu<T> build(List<Menu<T>> nodes) {



        if (nodes == null) {

            return null;

        }

        List<Menu<T>> topNodes = new ArrayList<Menu<T>>();



        for (Menu<T> children : nodes) {



            String pid = children.getParentId();

            if (pid == null || "0".equals(pid)) {

                topNodes.add(children);



                continue;

            }



            for (Menu<T> parent : nodes) {

                String id = parent.getId();

                if (id != null && id.equals(pid)) {

                    parent.getChildren().add(children);

                    children.setHasParent(true);

                    parent.setHasChildren(true);

                    continue;

                }

            }



        }



        Menu<T> root = new Menu<T>();

        if (topNodes.size() == 1) {

            root = topNodes.get(0);

        } else {

            root.setId("-1");

            root.setParentId("");

            root.setHasParent(false);

            root.setHasChildren(true);

            root.setChecked(true);

            root.setChildren(topNodes);

            root.setText("顶级节点");

            Map<String, Object> state = new HashMap<>(16);

            state.put("opened", true);

            root.setState(state);

        }



        return root;

    }



    public static <T> List<Menu<T>> buildList(List<Menu<T>> nodes, String idParam) {

        if (nodes == null) {

            return null;

        }

        List<Menu<T>> topNodes = new ArrayList<Menu<T>>();



        for (Menu<T> children : nodes) {



            String pid = children.getParentId();

            if (pid == null || idParam.equals(pid)) {

                topNodes.add(children);



                continue;

            }



            for (Menu<T> parent : nodes) {

                String id = parent.getId();

                if (id != null && id.equals(pid)) {

                    parent.getChildren().add(children);

                    children.setHasParent(true);

                    parent.setHasChildren(true);



                    continue;

                }

            }



        }

        return topNodes;

    }
}
